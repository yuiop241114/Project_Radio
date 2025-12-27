import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'
import { BrowserRouter, Route, Routes, Outlet } from 'react-router-dom';

import MainPage from './pages/MainPage';
import Login from './components/common/Login';
import MainLayout from './pages/layout/MainLayout';
import SignUp from './components/common/SignUp';

function App() {
  return (
    <Routes>
      {/* Header 포함 영역 */}
      <Route element={<MainLayout />}>
        <Route path="/" element={<MainPage/>} />
      </Route>

      {/* Header 없는 페이지 */}
      <Route path="/login" element={<Login />} />
      <Route path="/signUp" element={<SignUp/>}/>
    </Routes>
  );
}

export default App
