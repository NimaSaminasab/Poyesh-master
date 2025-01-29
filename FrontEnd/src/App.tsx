import React from 'react';
import Layout from "./components/layout/layout";
import { Navigate, RouteObject, useRoutes } from "react-router-dom";

import Students from "./pages/students/students";
import AddStudent from "./pages/students/addStudent/addStudent";
import StudentDetails from "./pages/students/addStudent/StudentDetails"; 
import AddSupporter from './pages/addSupporter/addSupporter';
import Supporters from './pages/addSupporter/supporters';
import AddAccount from './pages/addAccount/addAccount';
import Accounts from './pages/addAccount/accounts';
import Exchanges from './pages/addExchange/exchanges';
import AddExchange from './pages/addExchange/addExchange';
import AddFamily from './pages/addFamily/addFamily';
import Familys from './pages/addFamily/familys';
import Payments from './pages/AddPayment/Payments';
import AddPayment from './pages/AddPayment/addPayment';
import AssignFamily from './pages/addFamily/assignFamily';
import SupporterDetails from './pages/addSupporter/SupporterDetails';


const routes: RouteObject[] = [
    {
        path: "/app",
        element: <Layout />,
        children: [
            {
                path: "students",
                element: <Students />
            },
            {
                path: "students/add",
                element: <AddStudent />
            },
            {
                path: "supporters",
                element: <Supporters />
            },
            {
                path: "supporters/add",
                element: <AddSupporter />
            },
            {
                path: "accounts",
                element: <Accounts />
            },
            {
                path: "accounts/add",
                element: <AddAccount />
            },
            {
                path: "exchanges",
                element: <Exchanges />
            },
            {
                path: "exchanges/add",
                element: <AddExchange />
            },
            {
                path: "familys",
                element: <Familys />
            },
            {
                path: "familys/add",
                element: <AddFamily />
            },
            {
                path: "familys/assign",
                element: <AssignFamily />
            },
            {
                path: "payments",
                element: <Payments />
            },
            {
                path: "payments/add",
                element: <AddPayment />
            },
            {
                path: "",
                element: <Navigate to="students" />
            }
        ]
    },
    {
        path: "/app/students/details/:id",
        element: <StudentDetails />
    },
    {
        path: "/app/supporters/details/:id",
        element: <SupporterDetails />
    },
    {
        path: "/login",
        element: <p>Login</p>
    },
    {
        path: "*",
        element: <Navigate to={"/app/students"} />
    }
];

function App() {
    const renderedRoutes = useRoutes(routes);
    return (
        <div className="App">
            {renderedRoutes}
        </div>
    );
}

export default App;
