import { Link } from 'react-router-dom'

export default function AuthLinks() {
  return (
    <div className="flex justify-center">
      <p className="text-custom-text-gray flex gap-x-2">
        <Link to="/user/redefinir-senha" className="text-white hover:underline">
          Esqueceu a senha?
        </Link>
        {'•'}
        <Link to="/cadastro" className="text-white hover:underline">
          Cadastrar-se
        </Link>
      </p>
    </div>
  )
}
