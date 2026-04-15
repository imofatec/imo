export default function ContinueLearningSkeleton() {
  return (
    <div className="animate-pulse overflow-hidden rounded-3xl border border-white/10 bg-[#14082f]">
      <div className="grid md:grid-cols-[280px_1fr]">
        <div className="h-44 w-full border-b border-white/10 bg-white/10 md:h-full md:border-r md:border-b-0" />

        <div className="space-y-4 p-5 md:p-6">
          <div className="space-y-2">
            <div className="h-4 w-32 rounded bg-white/10" />
            <div className="h-7 w-2/3 rounded bg-white/10" />
            <div className="h-4 w-1/3 rounded bg-white/10" />
          </div>

          <div className="space-y-2">
            <div className="h-4 w-24 rounded bg-white/10" />
            <div className="h-2 w-full rounded-full bg-white/10" />
          </div>

          <div className="flex justify-end">
            <div className="h-10 w-36 rounded-full bg-white/10" />
          </div>
        </div>
      </div>
    </div>
  )
}
