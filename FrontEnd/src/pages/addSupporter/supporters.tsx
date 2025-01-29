import React, {useEffect, useState} from 'react';
import { SupporterService } from '../../service/supporter.service';
import { SupporterModel } from '../../model/supporter.model';
import {toast} from "react-toastify";
import './supporters.scss' ;
import {Link} from "react-router-dom";
import {Button, Collapse, IconButton, Paper, Switch, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";
import DatePicker, {DayValue} from "@hassanmojab/react-modern-calendar-datepicker";
import {useForm} from "../../hooks/useForm";
import moment from "moment-jalaali";

const Supporters = () => {


    const [supporters, setSupporters] = useState<SupporterModel[]>([])
    const [searchBoxOpen, setSearchBoxOpen] = useState(false);
    const { registerControl, form } = useForm<SupporterModel>({
        initialForm: {
            id : 0 ,
            fornavn : "" ,
            etternavn : "" ,
            telefon : "" ,
            adresse : "" ,
            postnummer : "" ,
            poststed : "" ,
            betaltTilNa : 0 ,
            betaltTilNaToman : 0 ,
            aktiv : true
        },
    });
    useEffect(() => {
        SupporterService.getAllSupporters()
            .then(res => {
                setSupporters(res.data)
            }).catch(err => {
            toast.error(err.message)
        })
    }, [])

    function handleDetails(supporterId: number) {
        // Navigate to the details page or open a modal
        window.open(`/app/supporters/details/${supporterId}`, '_blank', 'width=800,height=600');
    }
    function handleSearch() {
        const searchParam = form.fornavn || form.etternavn || form.id; // Example: Prioritize fields for search
        if (!searchParam) {
            toast.error("Please provide a search parameter");
            return;
        }
    
        SupporterService.searchSupporter(searchParam)
            .then(res => {
                const result = Array.isArray(res.data) ? res.data : [res.data];
                setSupporters(result);
            })
            .catch(err => {
                toast.error(err.message);
            });
    }
    

   

    function handleChangeActivation(id: number, value: boolean) {
        if (window.confirm("Are you sure ?"))
            if (value) {
                SupporterService.reActive(id)
                    .then(res => {
                        const supporter = supporters.find(item => item.id === id)
                        if (supporter) {
                            supporter.aktiv = value;
                            setSupporters([...supporters])
                        }
                    }).catch(err => {
                    toast.error(err.message)
                })
            } else {
                SupporterService.deActive(id)
                    .then(res => {
                        const supporter = supporters.find(item => item.id === id)
                        if (supporter) {
                            supporter.aktiv = value;
                            setSupporters([...supporters])
                        }
                    }).catch(err => {
                    toast.error(err.message)
                })
            }

    }

    return (
        <div className="supporters">
            <header>
                <h1>Supporter List</h1>
                <IconButton><Search onClick={() => setSearchBoxOpen(open => !open)}/></IconButton>
                <Link to="/app/supporters/add"><Button variant="contained" color="primary">Create</Button></Link>
            </header>
            <Collapse in={searchBoxOpen}>
                <Paper className="supporters__search">
                    <TextField {...registerControl("search")} label="search"/>
                    <Button onClick={handleSearch}>Search</Button>
                </Paper>
            </Collapse>
            <table>
                <thead>
                <tr>
                    <th>id</th>
                    <th>fornavn</th>
                    <th>etternavn</th>
                    <th>telefon</th>
                    <th>adresse</th>
                    <th>postnummer</th>
                    <th>poststed</th>
                    <th>betaltTilNa</th>
                    <th>betaltTilNaToman</th>
                    <th>active</th>
                    <th>detaljer</th>
                </tr>
                </thead>
                <tbody>
                {supporters.map(supporter => <tr>
                    <td>{supporter.id}</td>
                    <td>{supporter.fornavn}</td>
                    <td>{supporter.etternavn}</td>
                    <td>{supporter.telefon}</td>
                    <td>{supporter.adresse}</td>
                    <td>{supporter.postnummer}</td>
                    <td>{supporter.poststed}</td>
                    <td>{supporter.betaltTilNa}</td>
                    <td>{supporter.betaltTilNaToman}</td>
                    <td><Switch onChange={(e, value) => handleChangeActivation(supporter.id, value)}
                                checked={supporter.aktiv}/></td>
                     <td>
                <Button 
                    variant="outlined" 
                    color="primary" 
                    onClick={() => handleDetails(supporter.id)}
                >
                    Detaljer
                </Button></td>
                </tr>)}
                </tbody>
            </table>
        </div>
    );
};

export default Supporters;
