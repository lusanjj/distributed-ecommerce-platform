import React from 'react';
import { Container, Typography, Box } from '@mui/material';

const DashboardPage = () => {
    return (
        <Container maxWidth="md" sx={{ mt: 8 }}>
            <Box sx={{ p: 4, boxShadow: 3, borderRadius: 3 }}>
                <Typography variant="h4" gutterBottom>
                    User Dashboard
                </Typography>
                <Typography variant="body1">
                    Welcome to the User dashboard! Here, you can manage users, orders, and products.
                </Typography>
            </Box>
        </Container>
    );
};

export default DashboardPage;

