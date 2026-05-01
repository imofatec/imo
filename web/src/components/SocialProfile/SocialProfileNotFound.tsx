import { Link } from 'react-router-dom'

type SocialProfileNotFoundProps = {
  title: string
  error: string | null
}

export default function SocialProfileNotFound({ title, error }: SocialProfileNotFoundProps) {
  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="mx-auto flex w-full max-w-4xl flex-1 px-4 py-10">
        <div className="w-full rounded-4xl border border-white/10 bg-[#14082f] p-8 text-center">
          <h1 className="mt-3 text-3xl font-bold text-white">{title}</h1>
          <p className="mt-3 text-sm text-white/65">
            {error ?? 'O perfil que voce tentou acessar nao esta disponivel.'}
          </p>
          <Link
            to="/"
            className="border-cyan/30 bg-cyan/10 text-cyan hover:bg-cyan/20 mt-6 inline-flex rounded-xl border px-4 py-2 text-sm font-medium transition"
          >
            Voltar ao inicio
          </Link>
        </div>
      </section>
    </main>
  )
}
