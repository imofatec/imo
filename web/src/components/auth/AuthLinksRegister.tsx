import { Link } from 'react-router-dom'

export default function AuthLinks() {
  return (
    <div className="flex justify-center">
      <p className="text-custom-text-gray flex gap-x-2">
        <Link to="/user/redefinir-senha" className="text-white hover:underline">
          Esqueceu a senha?
        </Link>
        {'•'}
        <Link to="/login" className="text-white hover:underline">
          Já possui conta?
        </Link>
      </p>
    </div>
  )
}
