import axios from "axios"
import { redirect } from "react-router-dom"

// CADASTRO
export async function signUp({ request }) {
    const data = await request.formData()
    const submission = {
        username: data.get('username'),
        email: data.get('email'),
        password: data.get('password'),
        confPassword: data.get('password-confirm')
    }
    try {
        await axios.post('/api/user/create', submission)
        return redirect('/login')
    } catch (err) {
        return err.response.data.message
    }
}
