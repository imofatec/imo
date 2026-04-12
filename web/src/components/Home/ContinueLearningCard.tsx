import LinkButton from '@/components/ui/LinkButton'

type Props = {
  title: string
  instructor: string
  progress: number
  imageUrl: string
  to?: string
}

export default function ContinueLearningCard({ title, instructor, progress, imageUrl }: Props) {
  return (
    <div className="overflow-hidden rounded-3xl border border-white/10 bg-[#14082f]">
      <div className="grid md:grid-cols-[280px_1fr]">
        <img
          src={imageUrl}
          alt={title}
          className="h-44 w-full border-b border-white/10 object-cover md:h-full md:border-r md:border-b-0"
        />

        <div className="space-y-4 p-5 md:p-6">
          <div>
            <p className="text-cyan text-xs font-medium tracking-wide uppercase">
              Continue aprendendo
            </p>
            <h3 className="mt-2 text-lg font-semibold text-white md:text-xl">{title}</h3>
            <p className="mt-1 text-sm text-white/60">{instructor}</p>
          </div>

          <div>
            <div className="mb-2 flex items-center justify-between text-xs text-white/70">
              <span>Progresso atual</span>
              <span>{progress}%</span>
            </div>
            <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
              <div className="bg-cyan h-full" style={{ width: `${progress}%` }} />
            </div>
          </div>

          <div className="flex justify-end">
            <LinkButton
              to={'/user/cursos'}
              variant="cyanOutline"
              className="text-cyan border-cyan/40 mt-0! w-auto rounded-full border px-4 py-2"
            >
              Retomar curso
            </LinkButton>
          </div>
        </div>
      </div>
    </div>
  )
}
