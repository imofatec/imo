import React from 'react';
import { Link } from 'react-router-dom';

export default function ErrorPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-4xl font-bold text-red-500">404 - Página Não Encontrada</h1>
      <p className="mt-4 text-white">A página que você está procurando não existe ou foi removida.</p>
      <Link to="/" className="mt-6 text-blue-500">Voltar para a página inicial</Link>
    </div>
  );
}