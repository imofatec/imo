import { Link } from 'react-router-dom'

type Props = {
  courseTitle: string
}

export default function WatchHeader({ courseTitle }: Props) {
  return (
    <section className="border-b border-white/10">
      <div className="mx-auto flex w-full max-w-360 flex-wrap items-center gap-4 px-4 py-5 sm:px-6 lg:px-8">
        <Link
          to="/user/cursos"
          className="rounded-xl border border-white/15 bg-white/5 px-4 py-2 text-sm font-medium text-white/80 transition hover:bg-white/10 hover:text-white"
        >
          Voltar
        </Link>

        <div className="min-w-0 flex-1">
          <p className="text-xs tracking-[0.22em] text-white/50 uppercase">Curso</p>
          <h1 className="truncate text-2xl font-bold text-white">{courseTitle}</h1>
        </div>
      </div>
    </section>
  )
}
