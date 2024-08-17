import React from 'react';
import Header from './Header';
import Footer from './Footer';
import { Outlet } from 'react-router-dom'; 
/*
    Esta página serve como um molde para todas outras. 
    */
export default function Layout( {children} ) {
  return ( 
    <div className="flex flex-col min-h-screen">
      <Header />
      <main className="flex-grow">
      <Outlet />
        </main>
      <Footer />
    </div>
  );
}
