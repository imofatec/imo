export default function SkeletonCourseCard() {
  return (
    <div className="animate-pulse overflow-hidden rounded-2xl border border-white/10 bg-white/5">
      <div className="h-44 w-full bg-white/10" />

      <div className="p-4">
        <div className="h-6 w-24 rounded-full bg-white/10" />

        <div className="mt-4 h-5 w-3/4 rounded bg-white/10" />

        <div className="mt-2 h-4 w-1/2 rounded bg-white/10" />

        <div className="mt-4 h-4 w-full rounded bg-white/10" />

        <div className="mt-2 h-4 w-5/6 rounded bg-white/10" />

        <div className="mt-5 h-11 w-full rounded-xl bg-white/10" />
      </div>
    </div>
  )
}
