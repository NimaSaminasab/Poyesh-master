import axios from "axios";
import {StudentModel} from "../model/student.model";

export class StudentService {
    static getAllStudents() {
        return axios.get<StudentModel[]>("/findAllElev")
    }

   
    static searchStudents(searchTerm: string | number) {
        const searchfor = `/findAElev/${searchTerm}`;
        return axios.get<StudentModel[]>(searchfor);
    }
    
    

    static createStudent(body: StudentModel) {
        return axios.post<StudentModel>("/createElev", body)
    }

    static deActive(id: number) {
        return axios.get<StudentModel>("/deactivateElev/" + id)
    }

    static reActive(id: number) {
        return axios.get<StudentModel>("/reactivateElev/" + id)
    }
}
