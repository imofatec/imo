import api from '@/api/api'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'

export async function updateUser({ request }) {
  const data = await request.formData()

  const username = data.get('username')
  const email = data.get('email')
  const password = data.get('password')
  const confPassword = data.get('password-confirm')

  if (password !== confPassword) {
    return { error: 'As senhas estão diferentes.' }
  }
  const user = {}

  if (username) user.username = username
  if (email) user.email = email
  if (password) user.password = password

  const [error, result] = await safeAwait(api.put(`/api/user/update`, user))

  if (error) {
    return { error: error.response.data.message }
  }

  return { success: 'Dados atualizados com sucesso!' }
}
