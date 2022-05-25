import React from 'react';
import { makeStyles } from '@material-ui/core/styles'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { DataGrid } from '@material-ui/data-grid';

const styles = makeStyles(theme => ({

	table: {
		minWidth: 400,
	},
}));



export default function EmployeePairsTable( {rows} ) {
	
	const columns = [
	  {
	    field: 'firstEmployee',
	    headerName: 'Employee ID #1',
	    minWidth: 100,
	    flex: 1,
	    editable: false,
	  },
	  {
	    field: 'secondEmployee',
	    headerName: 'Employee ID #2',
	    minWidth: 100,
	    flex: 1,
	    editable: false,
	  },
	  {
	    field: 'projectID',
	    headerName: 'Project ID',
	    minWidth: 100,
	    flex: 1,
	    editable: false,
	  },
	  {
	    field: 'daysWorked',
	    headerName: 'Days worked',
	    type: 'number',
	    minWidth: 100,
	    flex: 1,
	    editable: false,
	  },
	  
	];

	return (
		<div style={{display: 'flex', height: "100%", width: '100%' }}>
			<div style={{ flexGrow: 1 }}>
		 		<DataGrid
				style={{width: "80%",  marginLeft: "auto",  marginRight: "auto",}}
				autoHeight 
		        rows={rows}
		        columns={columns}
		        pageSize={5}
		        getRowId={(row) => row.firstEmployee + "-" + row.secondEmployee + "-" + row.projectID} //each E1-E2-Project triplet must be unique
		      />
			</div>
		</div>
	)
}