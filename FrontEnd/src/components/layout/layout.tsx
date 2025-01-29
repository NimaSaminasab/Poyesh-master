import React from 'react';
import Header from "../header/header";
import {Outlet} from "react-router-dom";
import './layout.scss'



const Layout = () => {
    return (
        <div className="layout">
            <Header/>
            <main>
                <Outlet/>

            </main>
        </div>
    );
};

export default Layout;
