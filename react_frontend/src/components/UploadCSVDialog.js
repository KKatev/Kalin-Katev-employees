import React from 'react';
import { makeStyles } from '@material-ui/core/styles'
import { useState, useEffect } from 'react';

import Button from '@material-ui/core/Button';
import Box from '@material-ui/core/Box';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormLabel from '@material-ui/core/FormLabel';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

const url = "/employees/uploadProjectPeriods"

const useStyles = makeStyles(theme => ({
	button: {
		marginLeft: "auto",
		marginRight: "auto",
	},
}));

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}

export default function UploadCSVDialog( {handleResponseFunc, setOpenFunc, open} ) {
	const [dataFile, setDataFile] = useState();
	const [fileName, setFileName] = useState("Select Data");
	const [separator, setSeparator] = useState(',');
    const [dateFormat, setDateFormat] = useState("yyyy-MM-dd")
    const [alertMessage, setAlertMessage] = useState("");
    
    
	const classes = useStyles();
	
    const handleClose = () => {
	    setFileName("Select Data")
	    setOpenFunc(false);
    };
    
    const handleAlertClose = (event, reason) => {
        if (reason === 'clickaway') {
          return;
        }
    
        setAlertMessage("");
      };

    const handleSeparatorChange = (event) => {
        setSeparator(event.target.value);
    };
	
    const handleUploadClick = event => {
        var file = event.target.files[0];

        const reader = new FileReader();
        var url = reader.readAsDataURL(file);

        setDataFile(event.target.files[0])
        setFileName(event.target.files[0].name)
    };	  

    const onSubmit = () => {
        const formData = new FormData();
      
        formData.append('separator', separator)
        formData.append('file', dataFile)
        
        if(dateFormat) {
			formData.append('dateFormat', dateFormat)
		}
        
        
        const requestOptions = {
            method:  'POST',
            body: formData
        };
        
        fetch(url, requestOptions)
	        .then( response => {
                if (response.ok) {
                    return response.json()
                } else {
                    response.text().then(text => setAlertMessage(response.status + ': ' + response.statusText + ", " + text))
                    
                    throw new Error(response.status + ': ' + response.statusText);
                }
            })
	        .then(data => handleResponseFunc(data))
            .then(handleClose)
            .catch((error) => {
                console.log(error)
              });
    }	
    
    const submitDisabled = () => {
        return dataFile == null || dataFile == undefined
    }

	
	return (
        <div>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" style={{display:'flex',alignItems:'center',justifyContent:'center'}}>
                <DialogTitle id="form-dialog-title"> Upload CSV </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Upload a file containing employee IDs, their project IDs and their assignment periods.
                    </DialogContentText>

                    <div>
                        <span>&nbsp;</span>
                    </div>

                    <input
                        accept=".csv"
                        id="contained-button-file"
                        type="file"
                        onChange={handleUploadClick}
                        hidden={true}
                    />
                    
				
                    <label htmlFor="contained-button-file">
                        <Button variant="contained" color="primary" component="span" fullWidth>
                            {fileName}
                        </Button>
                    </label>
                    
                    <div>
                        <span>&nbsp;&nbsp;</span>
                    </div>
                    
                	<TextField	
                        autoFocus
                        helperText={!dateFormat ? "Leaving this empty defaults to 'yyyy-MM-dd'" : ''}
                        margin="dense"
                        id="collection"
                        label="Date Format"
                        type="text"
                        defaultValue={dateFormat}
                        fullWidth
                        onChange={e => {
                            setDateFormat(e.target.value); 
                        }}
                    />
             
                    <div>
                        <span>&nbsp;&nbsp;</span>
                    </div>
                    <FormLabel component="legend">Choose CSV separator </FormLabel>
                    <RadioGroup  aria-label="gender" name="gender1" value={separator} onChange={handleSeparatorChange}>
                        <FormControlLabel value="," control={<Radio />} label="Comma" />
                        <FormControlLabel value=";" control={<Radio />} label="Semicolon" />
                        <FormControlLabel value=" " control={<Radio />} label="Space" />
                        <FormControlLabel value="other" control={<Radio />} label="Other (please input separator symbol)" />
                        <TextField
                            margin="dense"
                            id="other"
                            label="Other"
                            type="text"
                            fullWidth
                            disabled={separator !== "other"}
                            onChange={e => setSeparator(e.target.value)}
                        />
                    </RadioGroup>
                  

                </DialogContent>

                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button disabled={submitDisabled()} onClick={() => onSubmit()} color="primary">
                        Submit
                    </Button>
                </DialogActions>
            </Dialog>
            <Snackbar open={alertMessage !== ""} autoHideDuration={5000} onClose={handleAlertClose}>
                <Alert onClose={handleAlertClose} severity="error">
                    {alertMessage}
                </Alert>
            </Snackbar>
        </div>
		
	)


    
  
}