import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'

export async function registerRequest({ request }) {
  const data = await request.formData()
  const submission = {
    name: data.get('name'),
    email: data.get('email'),
    password: data.get('password'),
    confPassword: data.get('password-confirm'),
  }

  const [error] = await safeAwait(
    axiosInstance.post('/api/user', submission),
  )

  if (error) {
    return { error: error.response.data.message }
  }

  return redirect('/login')
}
