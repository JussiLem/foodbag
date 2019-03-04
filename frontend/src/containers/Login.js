import React, { Component } from 'react';
import { Button, FormGroup, FormControl, FormLabel } from "react-bootstrap";
import "./Login.css";
import { login } from '../util/ApiUtils';
import { Link } from 'react-router-dom';
import { ACCESS_TOKEN } from '../constants';
import { notification, Icon } from 'antd';

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


/*
    login() {
        let port = (window.location.port ? ':' + window.location.port : '');
        if (port === ":3000") {
            port = ":8080";
        }
        window.location.href = "//" + window.location.hostname + port + '/login';
    }
*/



    handleSubmit = async event => {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const loginRequest = Object.assign({}, values);
                login(loginRequest)
                    .then(response => {
                        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                        this.props.onLogin();
                    }).catch(error => {
                    if(error.status === 401) {
                        notification.error({
                            message: 'Polling App',
                            description: 'Your Username or Password is incorrect. Please try again!'
                        });
                    } else {
                        notification.error({
                            message: 'Polling App',
                            description: error.message || 'Sorry! Something went wrong. Please try again!'
                        });
                    }
                });
            }
        });
    };


    render() {
        return (
            <div className="Login">
                <form onSubmit={this.handleSubmit} className="login-form">
                    <FormGroup controlId="email" size="lg">
                        <FormLabel>Email</FormLabel>
                        <FormControl
                            prefix={<Icon type="user" />}
                            autoFocus
                            type="email"
                            placeholder="Enter email"

                        />
                    </FormGroup>
                    <FormGroup controlId="password" size="lg">
                        <FormLabel>Password</FormLabel>
                        <FormControl
                            prefix={<Icon type="lock" />}
                            type="password"
                            name="password"
                            placeholder="Password"
                        />
                    </FormGroup>
                    <Button
                        block
                        size="lg"
                        type="primary"
                        name="submit"
                        className="login-form-button"
                    >
                        Login
                    </Button>
                    Or <Link to="/signup">register now!</Link>
                </form>
            </div>
        )
    }
}