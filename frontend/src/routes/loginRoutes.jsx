import SignUp from '@/pages/SignUp'
import SignIn from '@/pages/SignIn'
import { signUp } from '@/requests/user/signup'
import { signIn } from '@/requests/user/signin'

const cadastro = {
  path: '/cadastro',
  element: <SignUp />,
  action: signUp,
}

const login = {
  path: '/login',
  element: <SignIn />,
  action: signIn,
}

const loginRoutes = [cadastro, login]

export default loginRoutes
