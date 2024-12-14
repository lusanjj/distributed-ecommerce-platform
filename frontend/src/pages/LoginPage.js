import React, { useState } from 'react';
import { TextField, Button, Container, Typography, Box, CircularProgress } from '@mui/material';
import { useDispatch } from 'react-redux';
import { setAuth, setRole } from '../redux/authSlice';
import axios from '../services/api';
import { jwtDecode } from 'jwt-decode';
import '../styles/LoginPage.css'; // 引入自定义 CSS 文件

const LoginPage = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredentials((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const response = await axios.post('/auth/login', credentials);
            const token = response.data.data.accessToken;
            const decoded = jwtDecode(token);

            dispatch(setAuth(token));
            dispatch(setRole(decoded.role));

            setLoading(false);
            window.location.href = decoded.role === 'admin' ? '/admin/dashboard' : '/dashboard';
        } catch (err) {
            setLoading(false);
            const errorMessage = err.response?.data?.message || 'Login failed. Please try again.';
            setError(errorMessage);
        }
    };

    return (
        <div className="login-page">
            <Container maxWidth="xs" className="login-container">
                <Box className="login-box" sx={{ p: 4, boxShadow: 5 }}>
                    <Typography variant="h4" className="login-title" gutterBottom>
                        Welcome Back!
                    </Typography>
                    {error && (
                        <Typography color="error" className="error-message">
                            {error}
                        </Typography>
                    )}
                    <form onSubmit={handleSubmit} className="login-form">
                        <TextField
                            label="Username"
                            name="username"
                            value={credentials.username}
                            onChange={handleChange}
                            fullWidth
                            margin="normal"
                            required
                            className="input-field"
                        />
                        <TextField
                            label="Password"
                            name="password"
                            type="password"
                            value={credentials.password}
                            onChange={handleChange}
                            fullWidth
                            margin="normal"
                            required
                            className="input-field"
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                            className="login-button"
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} color="inherit" /> : 'Login'}
                        </Button>
                    </form>
                </Box>
            </Container>
        </div>
    );
};

export default LoginPage;
