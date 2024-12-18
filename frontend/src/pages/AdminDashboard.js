import React from 'react';
import { Container, Typography, Box } from '@mui/material';

const AdminDashboard = () => {
    return (
        <Container maxWidth="md" sx={{ mt: 8 }}>
            <Box sx={{ p: 4, boxShadow: 3, borderRadius: 3 }}>
                <Typography variant="h4" gutterBottom>
                    Admin Dashboard
                </Typography>
                <Typography variant="body1">
                    Welcome to the admin dashboard! Here, you can manage users, orders, and products.
                </Typography>
            </Box>
        </Container>
    );
};

export default AdminDashboard;
