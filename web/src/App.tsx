import { Outlet } from 'react-router-dom'
import Header from '@/components/layout/Header'

export default function App() {
  return (
    <div className="bg-dark-purple flex min-h-screen flex-col text-white">
      <Header />
      <main className="flex flex-1">
        <Outlet />
      </main>
    </div>
  )
}
