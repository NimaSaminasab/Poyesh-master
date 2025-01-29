import React, {useEffect, useState} from 'react';
import {StudentService} from "../../service/student.service";
import {StudentModel} from "../../model/student.model";
import {toast} from "react-toastify";
import './students.scss'
import {Link} from "react-router-dom";
import {Button, Collapse, IconButton, Paper, Switch, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";
import DatePicker, {DayValue} from "@hassanmojab/react-modern-calendar-datepicker";
import {useForm} from "../../hooks/useForm";
import moment from "moment-jalaali";

const Students = () => {


    const [students, setStudents] = useState<StudentModel[]>([])
    const [searchBoxOpen, setSearchBoxOpen] = useState(false);
    const { registerControl, form } = useForm<StudentModel>({
        initialForm: {
            id: 0,
            fornavn: "" ,
            etternavn: "" ,
            personnummer: "" ,
            telefon1: "" ,
            telefon2: "" ,
            telefon3: "" ,
            city: "" ,
            fDato: "" ,
            skolenavn: "" ,
            behovSumPrManed: 0 ,
            motattSumTilNa: 0 ,
            bilde: "" ,
            film: "" ,
            aktiv : true 
        },
    });

    function handleDetails(studentId: number) {
        // Navigate to the details page or open a modal
        window.open(`/app/students/details/${studentId}`, '_blank', 'width=800,height=600');
    }

    useEffect(() => {
        StudentService.getAllStudents()
            .then(res => {
                setStudents(res.data)
            }).catch(err => {
            toast.error(err.message)
        })
    }, [])

    function handleSearch() {
        if (!form.search) {
            toast.error("Please enter a search term.");
            return;
        }
        StudentService.searchStudents(form.search) // Pass only the search field
            .then(res => {
                const result = Array.isArray(res.data) ? res.data : [res.data];
                setStudents(result);
            })
            .catch(err => {
                toast.error(err.message);
            });
    }
    
    
    

    function handleChangeActivation(id: number, value: boolean) {
        if (window.confirm("Are you sure ?"))
            if (value) {
                StudentService.reActive(id)
                    .then(res => {
                        const student = students.find(item => item.id === id)
                        if (student) {
                            student.aktiv = value;
                            setStudents([...students])
                        }
                    }).catch(err => {
                    toast.error(err.message)
                })
            } else {
                StudentService.deActive(id)
                    .then(res => {
                        const student = students.find(item => item.id === id)
                        if (student) {
                            student.aktiv = value;
                            setStudents([...students])
                        }
                    }).catch(err => {
                    toast.error(err.message)
                })
            }

    }

    return (
        <div className="students">
            <header>
                <h1>Student List</h1>
                <IconButton><Search onClick={() => setSearchBoxOpen(open => !open)}/></IconButton>
                <Link to="/app/students/add"><Button variant="contained" color="primary">Create</Button></Link>
            </header>
            <Collapse in={searchBoxOpen}>
                <Paper className="students__search">
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
                    <th>personnummer</th>
                    <th>telefon1</th>
                    <th>by</th>
                    <th>fDato</th>
                    <th>skolenavn</th>
                    <th>behovSumPrManed</th>
                    <th>motattSumTilNa</th>
                    <th>active</th>
                    <th>detaljer</th>

                </tr>
                </thead>
                <tbody>
    {students.map(student => (
        <tr key={student.id}>
            <td>{student.id}</td>
            <td>{student.fornavn}</td>
            <td>{student.etternavn}</td>
            <td>{student.personnummer}</td>
            <td>{student.telefon1}</td>
            <td>{student.city}</td>
            <td>{student.fDato}</td>
            <td>{student.skolenavn}</td>
            <td>{student.behovSumPrManed}</td>
            <td>{student.motattSumTilNa}</td>
            <td>
                <Switch 
                    onChange={(e, value) => handleChangeActivation(student.id, value)}
                    checked={student.aktiv} 
                />
            </td>
            <td>
                <Button 
                    variant="outlined" 
                    color="primary" 
                    onClick={() => handleDetails(student.id)}
                >
                    Detaljer
                </Button>
            </td>
        </tr>
    ))}
</tbody>

            </table>
        </div>
    );
};

export default Students;
