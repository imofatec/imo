import api from '@/api/api'
import { redirect } from 'react-router-dom'

export async function updateUser({ request }) {
  const data = await request.formData()

  const username = data.get('username')
  const email = data.get('email')
  const password = data.get('password')
  const confPassword = data.get('password-confirm')

  if (password!== confPassword) {
     return { error: 'As senhas estão diferentes.' }
  }



  const user = {}

  if (username) user.username = username
  if (email) user.email = email
  if (password) user.password = password

  console.log('dados enviados', user)

  try {
    await api.put(`/api/user/update`, user)
    return { success: 'Dados atualizados com sucesso!' }
  } catch (err) {
    return { error: err.response.data.message }
  }
}
