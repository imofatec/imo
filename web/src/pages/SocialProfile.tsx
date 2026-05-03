import AchievementCard from '@/components/UserAchievements/AchievementCard'
import SocialProfileBioSection from '@/components/SocialProfile/SocialProfileBioSection'
import SocialProfileActivitySection from '@/components/SocialProfile/SocialProfileActivitySection'
import SocialProfileHeader from '@/components/SocialProfile/SocialProfileHeader'
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
      <section className="mx-auto w-full max-w-6xl px-4 py-8">
        <div className="overflow-hidden rounded-4xl border border-white/10 bg-[#14082f] shadow-[0_30px_100px_rgba(0,0,0,0.35)]">
          <SocialProfileHeader name={profile.name} />

          <div className="space-y-6 px-6 py-8 lg:px-8">
            <div className="grid gap-6 xl:grid-cols-[320px_minmax(0,1fr)] xl:items-stretch">
              <SocialProfileSidebar
                name={profile.name}
                profileImageSrc={profileImageSrc}
                categories={categories}
              />

              <section className="flex h-full flex-col justify-center rounded-[1.75rem] border border-white/10 bg-[#10052b] p-6">

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
                  <p className="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 text-center text-sm text-white/60">
                    Ainda não tem nenhuma conquista.
                  </p>
                )}
              </section>
            </div>

            <SocialProfileBioSection bio={profile.bio} />

            <SocialProfileActivitySection
              recentCourses={profile.lastActivity.recentCourses}
              lastComment={profile.lastActivity.lastComment}
            />
          </div>
        </div>
      </section>
    </main>
  )
}
