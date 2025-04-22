import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { redirect, json } from 'react-router-dom'
import { registerSchema } from '@/schemas/userRegisterSchema'

export async function registerRequest({ request }) {
  const data = await request.formData()
  const submission = {
    name: data.get('name'),
    email: data.get('email'),
    password: data.get('password'),
    confPassword: data.get('confPassword'),
  }

  const result = registerSchema.safeParse(submission)

  if (!result.success) {
    const fieldErrors = result.error.flatten().fieldErrors
    const formError = result.error.flatten().formErrors[0]

    return json(
      {
        error:
          formError ||
          Object.values(fieldErrors).flat().join(', ') ||
          'Erro ao validar os dados',
        fieldErrors,
      },
      { status: 400 }
    )
  }

  const [error] = await safeAwait(
    axiosInstance.post('/api/user/create', submission),
  )

  if (error) {
    const errorMessage = error.response?.data?.message
    if (errorMessage === 'O email já existe') {
      return json(
        {
          fieldErrors: {
            email: ['Este e-mail já está em uso.'],
          },
        },
        { status: 400 }
      )
    }

    return json(
      {
        error: errorMessage || 'Erro ao registrar usuário.',
      },
      { status: 400 }
    )
  }

  return redirect('/login')
}
