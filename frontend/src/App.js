import React, { Component } from "react";
import { Link } from "react-router-dom";
import { Nav, Navbar, NavDropdown, NavItem } from "react-bootstrap";
import Routes from "./Routes";
import "./App.css";
import { LinkContainer } from "react-router-bootstrap";

class App extends Component {
    render() {
        return (
            <div className="App container">
                <Navbar bg="light" expand="lg">
                    <Navbar.Brand>
                        <Link to="/">FoodBag</Link>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse>
                        <Nav className="navbar-nav ml-auto">
                            <LinkContainer to="/signup">
                                <NavItem>Signup</NavItem>
                            </LinkContainer>
                            <LinkContainer to="/login">
                                <NavItem>Login</NavItem>
                            </LinkContainer>
                            <NavDropdown title="Dropdown" id="basic-nav-dropdown">
                                <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <Routes />
            </div>
        );
    }
}

export default App;