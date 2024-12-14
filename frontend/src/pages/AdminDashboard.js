import React from 'react';
import { Container, Typography, Button } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { clearAuth } from '../redux/authSlice';

const AdminDashboard = () => {
    const dispatch = useDispatch();
    const { role } = useSelector((state) => state.auth);

    const handleLogout = () => {
        dispatch(clearAuth());
        localStorage.removeItem('token');
        window.location.href = '/login';
    };

    return (
        <Container maxWidth="lg">
            <Typography variant="h4" gutterBottom>
                Admin Dashboard
            </Typography>
            <Typography variant="body1">Welcome, {role}</Typography>
            <Button variant="contained" color="secondary" onClick={handleLogout}>
                Logout
            </Button>
        </Container>
    );
};

export default AdminDashboard;
