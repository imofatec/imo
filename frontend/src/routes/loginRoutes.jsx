import SignUp from '@/pages/SignUp'
import SignIn from '@/pages/SignIn'
import { forgetPasswordRequest } from '@/requests/user/forgetPasswordRequest'
import { verificationCodeRequest } from '@/requests/user/verificationCodeRequest'
import { resetPasswordRequest } from '@/requests/user/resetPasswordRequest'
import UserForgetPassword from '@/pages/UserForgetPassword'
import { registerRequest } from '@/requests/user/registerRequest'
import { loginRequest } from '@/requests/user/loginRequest'


const cadastro = {
  path: '/cadastro',
  element: <SignUp />,
  action: registerRequest,
}

const login = {
  path: '/login',
  element: <SignIn />,
  action: loginRequest,
}

async function handleForgetPasswordAction({ request }) {
  const formData = await request.formData()

  if (formData.get('email')) {
    return await forgetPasswordRequest(formData)
  }

  if (formData.get('VerificationCode')) {
    return await verificationCodeRequest(formData)
  }

  if (formData.get('newPassword')) {
    return await resetPasswordRequest(formData)
  }

  return { error: 'Dados inválidos' }
}

const userForgetPassword = {
  path: '/user/redefinir-senha',
  element: <UserForgetPassword />,
  action: handleForgetPasswordAction,
}

const loginRoutes = [cadastro, login, userForgetPassword]

export default loginRoutes
