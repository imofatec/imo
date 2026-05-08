export default function AchievementCardSkeleton() {
  return (
    <div className="overflow-hidden rounded-3xl border border-white/10 bg-[#14082f] p-4">
      <div className="aspect-square animate-pulse rounded-2xl bg-white/7" />
      <div className="mt-4 h-4 animate-pulse rounded bg-white/7" />
      <div className="mt-3 h-2 animate-pulse rounded-full bg-white/7" />
      <div className="mt-3 h-3 w-16 animate-pulse rounded bg-white/7" />
    </div>
  )
}
