import SocialProfileBioSection from '@/components/SocialProfile/SocialProfileBioSection'
import SocialProfileHeader from '@/components/SocialProfile/SocialProfileHeader'
import SocialProfileInfoSection from '@/components/SocialProfile/SocialProfileInfoSection'
import SocialProfileNotFound from '@/components/SocialProfile/SocialProfileNotFound'
import SocialProfileSidebar from '@/components/SocialProfile/SocialProfileSidebar'
import SocialProfileSkeleton from '@/components/SocialProfile/SocialProfileSkeleton'
import { academicDegreeOptions, availableTimeOptions, experienceOptions } from '@/constants/userOptions'
import { useSocialProfilePage } from '@/hooks/useSocialProfilePage'
import { formatSocialProfileBirthDate, getSocialProfileOptionLabel } from '@/lib/socialProfile'
import { resolveProfileImageSrc } from '@/lib/resolveProfileImageSrc'
import { CalendarDays, Clock3, GraduationCap, Sparkles } from 'lucide-react'
import { useMemo } from 'react'
import { useParams } from 'react-router-dom'

export default function SocialProfilePage() {
  const { userId } = useParams()
  const { user, isLoading, error, bio, featuredAchievements } = useSocialProfilePage({ userId })

  const profileImageSrc = resolveProfileImageSrc(user?.profilePicturePath ?? null)
  const categories = user?.categoriesOfInterest ?? []

  const profileInfoCards = useMemo(
    () => [
      {
        label: 'Data de nascimento',
        value: formatSocialProfileBirthDate(user?.birthDate ?? null),
        icon: <CalendarDays size={20} />,
      },
      {
        label: 'Formacao academica',
        value: getSocialProfileOptionLabel(
          user?.academicDegree,
          academicDegreeOptions,
          'Nao informado'
        ),
        icon: <GraduationCap size={20} />,
      },
      {
        label: 'Nivel de experiencia',
        value: getSocialProfileOptionLabel(
          user?.experienceLevel,
          experienceOptions,
          'Nao informado'
        ),
        icon: <Sparkles size={20} />,
      },
      {
        label: 'Tempo disponivel',
        value: getSocialProfileOptionLabel(
          user?.availableTimePerDay,
          availableTimeOptions,
          'Nao informado'
        ),
        icon: <Clock3 size={20} />,
      },
    ],
    [user?.academicDegree, user?.availableTimePerDay, user?.birthDate, user?.experienceLevel]
  )

  if (isLoading) {
    return <SocialProfileSkeleton />
  }

  if (!user || error) {
    return <SocialProfileNotFound error={error} />
  }

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="mx-auto w-full max-w-6xl px-4 py-8">
        <div className="overflow-hidden rounded-4xl border border-white/10 bg-[#14082f] shadow-[0_30px_100px_rgba(0,0,0,0.35)]">
          <SocialProfileHeader name={user.name} />

          <div className="grid gap-8 px-6 py-8 lg:grid-cols-[320px_minmax(0,1fr)] lg:px-8">
            <SocialProfileSidebar
              name={user.name}
              profileImageSrc={profileImageSrc}
              categories={categories}
              featuredAchievements={featuredAchievements}
            />

            <div className="space-y-6">
              <SocialProfileBioSection bio={bio} />
              <SocialProfileInfoSection items={profileInfoCards} />
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}
