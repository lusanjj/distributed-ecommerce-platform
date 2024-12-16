import React from 'react';
import { Grid, Card, CardContent, CardMedia, Typography } from '@mui/material';

const ProductGrid = () => {
    const categories = [
        { title: 'Electronics', image: '/images/electronics.jpg' },
        { title: 'Clothing', image: '/images/clothing.jpg' },
        { title: 'Home Decor', image: '/images/home_decor.jpg' },
        { title: 'Books', image: '/images/books.jpg' },
    ];

    return (
        <Grid container spacing={4} sx={{ p: 4 }}>
            {categories.map((category, index) => (
                <Grid item xs={12} sm={6} md={3} key={index}>
                    <Card>
                        <CardMedia
                            component="img"
                            height="140"
                            image={category.image}
                            alt={category.title}
                        />
                        <CardContent>
                            <Typography variant="h6" gutterBottom>
                                {category.title}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
            ))}
        </Grid>
    );
};

export default ProductGrid;
