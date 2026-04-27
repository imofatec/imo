type AchievementsOverviewProps = {
  total: number
  unlockedCount: number
  lockedCount: number
}

export default function AchievementsOverview({
  total,
  unlockedCount,
  lockedCount,
}: AchievementsOverviewProps) {
  return (
    <div className="grid gap-4 md:grid-cols-3">
      <div className="rounded-3xl border border-white/10 bg-[#14082f] p-5">
        <p className="text-sm text-white/60">Total</p>
        <p className="mt-2 text-3xl font-bold text-white">{total}</p>
      </div>

      <div className="rounded-3xl border border-cyan/20 bg-cyan/10 p-5">
        <p className="text-sm text-cyan/80">Desbloqueadas</p>
        <p className="mt-2 text-3xl font-bold text-white">{unlockedCount}</p>
      </div>

      <div className="rounded-3xl border border-white/10 bg-[#14082f] p-5">
        <p className="text-sm text-white/60">Bloqueadas</p>
        <p className="mt-2 text-3xl font-bold text-white">{lockedCount}</p>
      </div>
    </div>
  )
}
