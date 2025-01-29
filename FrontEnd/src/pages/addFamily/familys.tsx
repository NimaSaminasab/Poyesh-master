import React, {useEffect, useState} from 'react';
import { FamilyService } from '../../service/family.service';
import { FamilyModel } from '../../model/family.model';
import {toast} from "react-toastify";
import './familys.scss'
import {Link} from "react-router-dom";
import {Button, Collapse, IconButton, Paper, Switch, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";
import { useForm } from '../../hooks/useForm';

const Familys = () => {


    const [familys,setFamilys] = useState<FamilyModel[]>([])
    const [searchBoxOpen, setSearchBoxOpen] = useState(false);
    const { registerControl, form } = useForm<FamilyModel>({
        initialForm: {
            id: 0 ,
            farFornavn: "" ,
            farEtternavn: "" ,
            morFornavn: "" ,
            morEtternavn: "" ,
            isAktiv: false ,
            sumMotatt : 0 ,
            sumMotattToman : 0  
        
            
        },
    });

    useEffect(() => {
        FamilyService.getAllFamily()
            .then(res => {
                setFamilys(res.data)
            }).catch(err => {
            toast.error(err.message)
        })
    }, [])

    function handleSearch() {
        if (!form.search) {
            toast.error("Please enter a search term.");
            return;
        }
        console.log("Search term:", form.search);
        FamilyService.searchFamily(form)
            .then(res => {
                console.log("Search result:", res.data);
                // Ensure students is always an array
                const result = Array.isArray(res.data) ? res.data : [res.data];
                setFamilys(result);
            })
            .catch(err => {
                toast.error(err.message);
            });
    }

    function handleChangeActivation(id: number, value: boolean) {
        if (window.confirm("Are you sure ?"))
            if (value) {
                FamilyService.reActive(id)
                    .then(res => {
                        const family = familys.find(item => item.id === id)
                        if (family) {
                            family.isAktiv = value;
                            setFamilys([...familys])
                        }
                    }).catch(err => {
                    toast.error(err.message)
                })
            } else {
                FamilyService.deActive(id)
                    .then(res => {
                        const family = familys.find(item => item.id === id)
                        if (family) {
                            family.isAktiv = value;
                            setFamilys([...familys])
                        }
                    }).catch(err => {
                    toast.error(err.message)
                })
            }

    }

    return (
        <div className="familys">
            <header>
                <h1>Family List</h1>
                <IconButton><Search onClick={() => setSearchBoxOpen(open => !open)}/></IconButton>
                <Link to="/app/familys/add"><Button variant="contained" color="primary">Create</Button></Link>
                <Link to="/app/familys/assign"><Button variant="contained" color="primary">Assign</Button></Link>
      
            </header>
            <Collapse in={searchBoxOpen}>
                <Paper className="familys__search">
                    
                    <TextField {...registerControl("search")} label="search"/>
                    <Button onClick={handleSearch}>Search</Button>
                </Paper>
            </Collapse>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Far fornavn</th>
                    <th>Far etternavn</th>
                    <th>Mor fornavn</th>
                    <th>Mor etternavn</th>
                    <th>sum Motatt</th>
                    <th>sum Motatt toman</th>
                      <th>active</th>
                </tr>
                </thead>
                <tbody>
                {familys.map(family => <tr>
                    <td>{family.id}</td>
                    <td>{family.farFornavn}</td>
                    <td>{family.farEtternavn}</td>
                    <td>{family.morFornavn}</td>
                    <td>{family.morEtternavn}</td>
                    <td>{family.sumMotatt}</td>
                    <td>{family.sumMotattToman}</td>
                    <td>
                                <Switch 
                                    onChange={(e, value) => handleChangeActivation(family.id, value)} 
                                    checked={family.isAktiv ?? false}  
                                />
                            </td>
                </tr>)}
                </tbody>
            </table>
        </div>
    );
};

export default Familys;
