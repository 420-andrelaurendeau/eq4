import React, {Component} from 'react';
import {useNavigate} from "react-router-dom";
import {Navbar, NavbarBrand} from "reactstrap";
function EmployerNavBar(){
    const navigate = useNavigate();
    const handleNavigation = () => {
        navigate("/employers/");
        window.location.reload();
    }

    return <Navbar>
        <NavbarBrand onClick={handleNavigation}>Home</NavbarBrand>
    </Navbar>
}
export default Navbar;