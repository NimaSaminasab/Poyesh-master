import React from 'react';
import { Link } from 'react-router-dom';

import './header.scss'

const Header = () => {
    return (
      <header className="header">
           
      
     

            <nav>
                <ul>
                    <li><Link to  ="/app/Students">Students</Link></li>
                    <li><Link to  ="/app/Supporters">Supporters</Link></li>
                    <li><Link to  ="/app/Familys">Family</Link></li>
                    <li><Link to  ="/app/Payments">Payments</Link></li>
                    <li><Link to  ="/app/Accounts">Account</Link></li>
                    <li><Link to  ="/app/Exchanges">Exchanges</Link></li>
                   
                </ul>
            </nav>
        </header>
    );
};

export default Header;




