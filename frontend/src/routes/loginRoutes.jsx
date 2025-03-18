import SignUp from '@/pages/SignUp'
import SignIn from '@/pages/SignIn'
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

const loginRoutes = [cadastro, login]

export default loginRoutes
