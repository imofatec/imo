import LinkButton from '@/components/ui/LinkButton'

export default function NotFoundPage() {
  return (
    <section className="flex min-h-full w-full items-center justify-center px-4 py-16 sm:px-6 lg:px-8">
      <div className="w-full max-w-2xl rounded-3xl border border-white/10 bg-white/5 p-10 text-center shadow-2xl">
        <p className="text-cyan text-sm font-semibold tracking-[0.3em] uppercase">404</p>
        <h1 className="mt-4 text-4xl font-bold text-white sm:text-5xl">
          Página não encontrada
        </h1>
        <p className="mt-4 text-sm leading-7 text-white/70 sm:text-base">
          O link que você tentou acessar não existe ou foi movido.
        </p>

        <div className="mt-8 flex justify-center">
          <LinkButton variant="cyanOutline" to="/" className="text-cyan">
            Voltar para o início
          </LinkButton>
        </div>
      </div>
    </section>
  )
}
