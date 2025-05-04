import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export async function updateUserRequest({ request }) {
  const data = await request.formData()

  const name = data.get('name')
  const email = data.get('email')
  const password = data.get('password')
  const confPassword = data.get('password-confirm')

  if (password !== confPassword) {
    return { error: 'As senhas estão diferentes.' }
  }
  const user = {}

  if (name) user.name = name
  if (email) user.email = email
  if (password) user.password = password

  const [error, result] = await safeAwait(
    authAxiosInstance.put(`/api/user`, user),
  )

  if (error) {
    return { error: error.response.data.message }
  }

  if (result.status === 204) {
    return { success: 204 }
  }

  return { success: 'Atualizado com sucesso' }
}
