import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import Login from "./components/common/Login"
import SignUp from './components/common/SignUp'
import MainPage from './pages/MainPage'

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainPage/>}/>
      <Route path="/login" element={<Login/>}/>
    </Routes>
  );
}

export default App
