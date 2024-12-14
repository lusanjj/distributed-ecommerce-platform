/**
 * File: LoginPage.js
 * Description: Handles user login, processes backend API response, and stores tokens.
 * Author: Shane Liu
 * Date: 2024/12/13
 */

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../services/api';

/**
 * LoginPage component for user authentication.
 */
const LoginPage = () => {
    // State to store user input
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // State to manage error messages
    const [error, setError] = useState(null);

    // React Router's navigate function for page redirection
    const navigate = useNavigate();

    /**
     * Handle form submission for login.
     * Sends user credentials to the login API.
     */
    const handleSubmit = async (event) => {
        event.preventDefault();
        setError(null);

        try {
            // Make API call to login endpoint
            const response = await apiClient.post('/auth/login', {
                username,
                password,
            });

            // Extract tokens from the wrapped response
            const { accessToken, refreshToken } = response.data.data;

            // Save tokens to localStorage
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);

            // Navigate to the dashboard after successful login
            navigate('/dashboard');
        } catch (err) {
            // Handle API error response
            setError(err.response?.data?.message || 'Login failed. Please try again.');
        }
    };

    /**
     * Render the LoginPage component.
     */
    return (
        <div style={{ maxWidth: '400px', margin: 'auto', padding: '1rem' }}>
            <h2>Login</h2>
            {error && (
                <div style={{ color: 'red', marginBottom: '1rem' }}>{error}</div>
            )}
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '1rem' }}>
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                        style={{ width: '100%', padding: '0.5rem' }}
                    />
                </div>
                <div style={{ marginBottom: '1rem' }}>
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={{ width: '100%', padding: '0.5rem' }}
                    />
                </div>
                <button type="submit" style={{ width: '100%', padding: '0.5rem' }}>
                    Login
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
