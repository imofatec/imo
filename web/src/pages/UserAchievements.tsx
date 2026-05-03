import AchievementsGrid from '@/components/UserAchievements/AchievementsGrid'
import AchievementsOverview from '@/components/UserAchievements/AchievementsOverview'
import FormSection from '@/components/CreateCourses/FormSection'
import { useUserAchievementsPage } from '@/hooks/useUserAchievementsPage'
import { Link } from 'react-router-dom'

export default function UserAchievementsPage() {
  const { achievements, isLoading } = useUserAchievementsPage()

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="mx-auto flex w-full max-w-5xl items-center justify-between gap-4 px-4 py-5">
          <div>
            <h1 className="text-2xl font-bold text-white">Conquistas</h1>
            <p className="mt-1 text-sm text-white/60">
              Veja todas as conquistas disponiveis na plataforma.
            </p>
          </div>

          <Link
            to="/user/configuracoes"
            className="border-cyan/30 bg-cyan/10 text-cyan hover:bg-cyan/20 rounded-xl border px-4 py-2 text-sm font-medium transition"
          >
            Voltar ao perfil
          </Link>
        </div>
      </section>

      <section className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
        <AchievementsOverview
          total={achievements.total}
          unlockedCount={achievements.unlockedCount}
          lockedCount={achievements.lockedCount}
        />

        <FormSection title="Colecao completa">
          <AchievementsGrid items={achievements.items} isLoading={isLoading} />
        </FormSection>
      </section>
    </main>
  )
}
