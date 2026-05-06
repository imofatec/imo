import AchievementCard from '@/components/UserAchievements/AchievementCard'
import SocialProfileBioSection from '@/components/SocialProfile/SocialProfileBioSection'
import SocialProfileActivitySection from '@/components/SocialProfile/SocialProfileActivitySection'
import SocialProfileHeader from '@/components/SocialProfile/SocialProfileHeader'
import SocialProfileMilestonesSection from '@/components/SocialProfile/SocialProfileMilestonesSection'
import SocialProfileNotFound from '@/components/SocialProfile/SocialProfileNotFound'
import SocialProfileSidebar from '@/components/SocialProfile/SocialProfileSidebar'
import SocialProfileSkeleton from '@/components/SocialProfile/SocialProfileSkeleton'
import { useSocialProfilePage } from '@/hooks/useSocialProfilePage'
import { resolveProfileImageSrc } from '@/lib/resolveProfileImageSrc'
import { useParams } from 'react-router-dom'

export default function SocialProfilePage() {
  const { userId } = useParams()
  const { profile, isLoading, error, errorType, highlightedAchievements } = useSocialProfilePage({
    userId,
  })

  const profileImageSrc = resolveProfileImageSrc(profile?.profilePicturePath ?? null)
  const categories = profile?.categoriesOfInterest ?? []

  if (isLoading) {
    return <SocialProfileSkeleton />
  }

  if (!profile) {
    return (
      <SocialProfileNotFound
        title={errorType === 'not_found' ? 'Nao encontramos este usuário' : 'Nao foi possível carregar este perfil'}
        error={error}
      />
    )
  }

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="mx-auto w-full max-w-[1400px] px-4 py-8">
        <SocialProfileHeader name={profile.name} />

        <div className="px-6 py-8 lg:px-8">
          <div className="grid gap-8 xl:grid-cols-[240px_minmax(0,1fr)] 2xl:gap-10 xl:items-stretch">
            <SocialProfileSidebar
              name={profile.name}
              profileImageSrc={profileImageSrc}
              categories={categories}
            />

            <SocialProfileBioSection bio={profile.bio} />
          </div>

          <div className="mt-8 space-y-8 border-t border-white/10 pt-8">
            <section>
              <div className="mb-5">
                <h2 className="text-lg font-semibold text-white">Conquistas em destaque</h2>
                <p className="mt-2 max-w-3xl text-sm leading-6 text-white/60">
                  Últimas conquistas desbloqueadas por este usuário.
                </p>
              </div>

              {highlightedAchievements.length ? (
                <div className="grid grid-cols-1 gap-4 md:grid-cols-2 xl:grid-cols-3">
                  {highlightedAchievements.map((achievement) => (
                    <AchievementCard
                      key={achievement.key}
                      achievement={achievement}
                      hideProgress
                    />
                  ))}
                </div>
              ) : (
                <p className="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 text-sm text-white/60">
                  Ainda não tem nenhuma conquista.
                </p>
                )}
              </section>

            <div className="border-t border-white/10 pt-8">
              <SocialProfileMilestonesSection milestones={profile.sharedProgressMilestones} />
            </div>

            <div className="border-t border-white/10 pt-8">
              <SocialProfileActivitySection
                recentCourses={profile.lastActivity.recentCourses}
                lastComment={profile.lastActivity.lastComment}
              />
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}
