import React, {useEffect, useState} from 'react';
import { ExchangeModel } from '../../model/exchange.mode';
import { ExchangeService } from '../../service/exchange.service';
import {toast} from "react-toastify";
import './exchanges.scss'
import {Link} from "react-router-dom";
import {Button, Collapse, IconButton, Paper, Switch, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";
import DatePicker, {DayValue} from "@hassanmojab/react-modern-calendar-datepicker";
import {useForm} from "../../hooks/useForm";
import moment from "moment-jalaali";

const Exchanges = () => {

    const [Date, setDate] = useState({
        year: moment().year(),
        month: moment().month() + 1,
        day: moment().date(),
    });
    const [Exchanges, setExchanges] = useState<ExchangeModel[]>([])
    const [searchBoxOpen, setSearchBoxOpen] = useState(false);
    const { registerControl, form } = useForm<ExchangeModel>({
        initialForm: {
            id: 0,
            date:"" ,
            nok:  0,
            toman: 0,
            kurs: 0,
            gebyr: 0,
        },
    });

    useEffect(() => {
        ExchangeService.getAllExchange()
            .then(res => {
                setExchanges(res.data)
            }).catch(err => {
            toast.error(err.message)
        })
    }, [])

    function handleSearch() {
        const formattedDate = `${Date.year}-${String(Date.month).padStart(2, '0')}-${String(Date.day).padStart(2, '0')}`;
    
        const searchForm = {
            ...form,
            date: formattedDate, // Send formatted date
        };
    
        ExchangeService.searchExchange(searchForm)
            .then((res) => {
                setExchanges(res.data);
                toast.success("Search completed successfully");
            })
            .catch((err) => {
                toast.error(err.message);
            });
    }
    
    

    return (
        <div className="exchanges">
            <header>
                <h1>Exchange List</h1>
                <IconButton><Search onClick={() => setSearchBoxOpen(open => !open)}/></IconButton>
                <Link to="/app/exchanges/add"><Button variant="contained" color="primary">Create</Button></Link>
            </header>
            <Collapse in={searchBoxOpen}>
                <Paper className="exchanges__search">
                <DatePicker
                    value={Date}
                    onChange={(value) => {
                        if (value) setDate(value);
                    }}
                    locale="en"
                />
                
                
                    <Button onClick={handleSearch}>Search</Button>
                </Paper>
            </Collapse>
            <table>
                <thead>
                <tr>
                    <th>id</th>
                    <th>date</th>
                    <th>nok</th>
                    <th>toman</th>
                    <th>kurs</th>
                    <th>gebyr</th>
                   
                </tr>
                </thead>
                <tbody>
    {Array.isArray(Exchanges) && Exchanges.length > 0 ? (
        Exchanges.map((Exchange) => (
            <tr key={Exchange.id}>
                <td>{Exchange.id}</td>
                <td>{Exchange.date}</td>
                <td>{Exchange.nok}</td>
                <td>{Exchange.toman}</td>
                <td>{Exchange.kurs}</td>
                <td>{Exchange.gebyr}</td>
            </tr>
        ))
    ) : (
        <tr>
            <td colSpan={6}><h2>No exchanges found</h2></td>
        </tr>
    )}
</tbody>

            </table>
        </div>
    );
};

export default Exchanges;
