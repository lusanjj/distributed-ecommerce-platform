import { createSlice } from '@reduxjs/toolkit';

const authSlice = createSlice({
    name: 'auth',
    initialState: { token: null, role: null },
    reducers: {
        setAuth(state, action) {
            state.token = action.payload;
        },
        setRole(state, action) {
            state.role = action.payload;
        },
        clearAuth(state) {
            state.token = null;
            state.role = null;
        },
    },
});

export const { setAuth, setRole, clearAuth } = authSlice.actions;
export default authSlice.reducer;
