import { Outlet } from 'react-router-dom'
import Header from '@/components/layout/Header'
import Breadcrumbs from '@/components/layout/Breadcrumbs'

export default function App() {
  return (
    <div className="bg-dark-purple flex min-h-screen flex-col text-white">
      <Header />
      <Breadcrumbs />
      <main className="flex flex-1">
        <Outlet />
      </main>
    </div>
  )
}
