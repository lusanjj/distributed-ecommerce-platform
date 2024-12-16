import React from 'react';
import { Box, Typography } from '@mui/material';

const Footer = () => {
    return (
        <Box sx={{ background: '#333', color: '#fff', p: 2, textAlign: 'center' }}>
            <Typography variant="body2">
                &copy; {new Date().getFullYear()} MyShop. All Rights Reserved.
            </Typography>
        </Box>
    );
};

export default Footer;
