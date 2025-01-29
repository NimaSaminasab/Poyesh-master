import React, {useEffect, useState} from 'react';
import { AccountService } from '../../service/account.service';
import { AccountModel } from '../../model/Account.model';
import {toast} from "react-toastify";
import './accounts.scss'
import {Link} from "react-router-dom";
import {Button, Collapse, IconButton, Paper, Switch, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";
import DatePicker, {DayValue} from "@hassanmojab/react-modern-calendar-datepicker";
import {useForm} from "../../hooks/useForm";
import moment from "moment-jalaali";

const Accounts = () => {


    const [Accounts, setAccounts] = useState<AccountModel[]>([])
    const [searchBoxOpen, setSearchBoxOpen] = useState(false);
    const { registerControl, form } = useForm<AccountModel>({
        initialForm: {
            id : 0 ,
            bankNavn  : "", 
            kontoHoldersNavn : "",
            kontoNummer : "", 
            shebaNummer : "",
            kortNummer : "" ,
            
        },
    });

    useEffect(() => {
        AccountService.getAllAccount()
            .then(res => {
                console.log("Accounts data:", res.data); // Debugging: Log response
                const accountsWithElevFullName = res.data.map(account => ({
                    ...account,
                    elevFullName: account.elev ? `${account.elev.fornavn} ${account.elev.etternavn}` : 'N/A',
                }));
                setAccounts(accountsWithElevFullName);
            })
            .catch(err => {
                toast.error(err.message);
            });
    }, []);
    
    

    function handleSearch() {
        AccountService.searchAccount(form).then(res => {
            const accountsWithElevFullName = res.data.map(account => ({
                ...account,
                elevFullName: account.elev ? `${account.elev.fornavn} ${account.elev.etternavn}` : 'N/A', // Map full name
            }));
            setAccounts(accountsWithElevFullName);
        }).catch(err => {
            toast.error(err.message);
        });
    }
    
    
    
    return (
        <div className="accounts">
            <header>
                <h1>Account List</h1>
                <IconButton><Search onClick={() => setSearchBoxOpen(open => !open)}/></IconButton>
                <Link to="/app/accounts/add"><Button variant="contained" color="primary">Create</Button></Link>
            </header>
            <Collapse in={searchBoxOpen}>
                <Paper className="accounts__search">
                <TextField {...registerControl("search")} label="search"/> 
                <Button onClick={handleSearch}>Search</Button>
                </Paper>
            </Collapse>
            <table>
                <thead>
                <tr>
                    <th>id</th>
                    <th>elev</th>
                    <th>banknavn</th>
                    <th>kontoHoldersNavn</th>
                    <th>kontoNummer</th>
                    <th>shebaNummer</th>
                    <th>kortNummer</th>
                   
                </tr>
                </thead>
                <tbody>
    {Accounts.map(account => (
        <tr key={account.id}>
            <td>{account.id}</td>
            <td>{account.elevFullName || 'N/A'}</td> {/* Display full name */}
            <td>{account.bankNavn}</td>
            <td>{account.kontoHoldersNavn}</td>
            <td>{account.kontoNummer}</td>
            <td>{account.shebaNummer}</td>
            <td>{account.kortNummer}</td>
        </tr>
    ))}
</tbody>
            </table>
        </div>
    );
};

export default Accounts;
