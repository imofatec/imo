import api from "@/api/api"
import { useEffect, useState } from "react"
import { Navigate, Outlet } from "react-router-dom"

export default function ProtectedRoute() {
    const [isAuth, setIsAuth] = useState(false)
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        const auth = async () => {
            try {
                await api.get('/api/private')
                setIsAuth(true)
            } catch (err) {
                // console.log(err)
            } finally {
                setIsLoading(false)
            }
        }
        auth()
    }, [])

    if (isLoading === false) {
        return isAuth ? <Outlet /> : <Navigate to="/login" replace />
    }
}
