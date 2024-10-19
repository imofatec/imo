import axios from 'axios'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'

// CADASTRO
export async function signUp({ request }) {
  const data = await request.formData()
  const submission = {
    username: data.get('username'),
    email: data.get('email'),
    password: data.get('password'),
    confPassword: data.get('password-confirm'),
  }

  const [error, result] = await safeAwait(
    axios.post('/api/user/create', submission),
  )

  if (error) {
    return error.response.data.message || ' '
  }

  return redirect('/login')
}
