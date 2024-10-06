import axios from "axios"
import { redirect } from "react-router-dom"

// LOGIN
export async function signIn({ request }) {
    const data = await request.formData()
    const submission = {
        email: data.get('email'),
        password: data.get('password')
    }

    try {
        const result = await axios.post('/api/user/login', submission)
        console.log(result.data)
        const { acessToken } = result.data
        localStorage.setItem('token', acessToken)
        return redirect('/private')
    } catch (err) {
        return err.response.data.message
    }
}
