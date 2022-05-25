import logo from './logo.svg';
import './App.css';
import { useState } from 'react';

import Button from '@material-ui/core/Button';

import EmployeePairsTable from './components/EmployeePairTable'
import UploadCSVDialog from './components/UploadCSVDialog'

function App() {	
	const [rows, setRows] = useState([])
	const [summary, setSummary] = useState("no data present")
	const [uploadDialogOpen, setUploadDialogOpen] = useState(false)
	
	const applyData = responseData => {
		setRows(responseData.list)
		setSummary("".concat(responseData.firstEmployee, ", ", responseData.secondEmployee, ", ", responseData.workdays))
	}
	
	return (
		<div className="App">
			<header className="App-header">
				<img src={logo} className="App-logo" alt="logo" />
			</header>
			<body>
			
			<div style={{ margin: 32 }}>
		      	<Button variant="contained" color="primary" component="span" onClick={() => setUploadDialogOpen(true)}>
	          	Upload CSV
	        	</Button>
		    </div>
        	<p>
			Employees summary: {summary}
			</p>
			<EmployeePairsTable rows={rows}/>
			
			</body>
		<UploadCSVDialog handleResponseFunc={(data) => applyData(data)} setOpenFunc={setUploadDialogOpen} open={uploadDialogOpen} />
		</div>

	);
}

export default App;
