import { safeAwait } from '@/lib/safeAwait'
import axios from 'axios'
import { redirect } from 'react-router-dom'

// LOGIN
export async function signIn({ request }) {
  const data = await request.formData()
  const submission = {
    email: data.get('email'),
    password: data.get('password'),
  }

  const [error, result] = await safeAwait(
    axios.post('/api/user/login', submission),
  )

  if (error) {
    return error.response.data.message || ' '
  }

  const { acessToken } = result.data
  localStorage.setItem('token', acessToken)
  return redirect('/categorias')
}
