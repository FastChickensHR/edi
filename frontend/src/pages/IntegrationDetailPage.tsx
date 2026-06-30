import { useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { useAuth } from '@/hooks/useAuth'
import { Badge } from '@/components/Badge'
import { Button } from '@/components/Button'
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/Tabs'
import { X834ConfigForm } from '@/features/integrations/components/X834ConfigForm'
import { useIntegration } from '@/features/integrations/hooks/useIntegrations'
import {
  useX834Config,
  useCreateX834Config,
  useUpdateX834Config,
} from '@/features/integrations/hooks/useX834Config'

const PageHeader = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: ${({ theme }) => theme.spacing[6]};
  gap: ${({ theme }) => theme.spacing[4]};
`

const HeaderLeft = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing[1]};
`

const PageTitle = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

const Subtitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[500]};
  margin: 0;
`

const TabsContainer = styled.div`
  background-color: ${({ theme }) => theme.colors.white};
  border: 1px solid ${({ theme }) => theme.colors.gray[200]};
  border-radius: ${({ theme }) => theme.borderRadius.lg};
  overflow: hidden;
`

const TabPanel = styled.div`
  padding: ${({ theme }) => theme.spacing[6]};
`

const DetailGrid = styled.dl`
  display: grid;
  grid-template-columns: max-content 1fr;
  gap: ${({ theme }) => `${theme.spacing[3]} ${theme.spacing[6]}`};
  margin: 0;
`

const DetailLabel = styled.dt`
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[500]};
  white-space: nowrap;
`

const DetailValue = styled.dd`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

function formatSystem(system: string): string {
  const map: Record<string, string> = {
    FASTCHICKENS_HR: 'FastChickensHR',
    STATE_OF_MICHIGAN: 'State of Michigan',
    WORKDAY: 'Workday',
    ADP: 'ADP',
    BENEFITFOCUS: 'Benefitfocus',
  }
  return map[system] ?? system
}

function formatDate(iso: string): string {
  return new Date(iso).toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
}

export function IntegrationDetailPage() {
  const { integrationId } = useParams<{ integrationId: string }>()
  const navigate = useNavigate()
  const { memberRole } = useAuth()

  const { data: integration, isLoading: integrationLoading } = useIntegration(integrationId!)
  const { data: config, isLoading: configLoading } = useX834Config(integrationId!)
  const createConfig = useCreateX834Config(integrationId!)
  const updateConfig = useUpdateX834Config(integrationId!)

  const canMutate = memberRole !== 'VIEWER'

  if (integrationLoading) {
    return <p style={{ color: 'var(--color-gray-500)', fontSize: '0.875rem' }}>Loading…</p>
  }

  if (!integration) {
    return <p style={{ color: 'var(--color-gray-500)', fontSize: '0.875rem' }}>Integration not found.</p>
  }

  const direction = integration.direction?.toLowerCase() as 'inbound' | 'outbound'

  return (
    <>
      <PageHeader>
        <HeaderLeft>
          <PageTitle>{integration.name}</PageTitle>
          <Subtitle>
            {formatSystem(integration.fromSystem)} → {formatSystem(integration.toSystem)}
          </Subtitle>
        </HeaderLeft>
        <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center' }}>
          <Badge $variant={direction === 'inbound' ? 'inbound' : 'outbound'}>
            {integration.direction}
          </Badge>
          {canMutate && (
            <Button
              $variant="secondary"
              $size="sm"
              onClick={() => navigate(`/integrations/${integrationId}/edit`)}
            >
              Edit
            </Button>
          )}
        </div>
      </PageHeader>

      <Tabs defaultValue="details">
        <TabsContainer>
          <TabsList>
            <TabsTrigger value="details">Details</TabsTrigger>
            <TabsTrigger value="x834config">X834 Config</TabsTrigger>
          </TabsList>

          <TabsContent value="details">
            <TabPanel>
              <DetailGrid>
                <DetailLabel>Name</DetailLabel>
                <DetailValue>{integration.name}</DetailValue>
                <DetailLabel>From</DetailLabel>
                <DetailValue>{formatSystem(integration.fromSystem)}</DetailValue>
                <DetailLabel>To</DetailLabel>
                <DetailValue>{formatSystem(integration.toSystem)}</DetailValue>
                <DetailLabel>Format</DetailLabel>
                <DetailValue>{integration.format}</DetailValue>
                <DetailLabel>Direction</DetailLabel>
                <DetailValue>
                  <Badge $variant={direction === 'inbound' ? 'inbound' : 'outbound'}>
                    {integration.direction}
                  </Badge>
                </DetailValue>
                <DetailLabel>Created</DetailLabel>
                <DetailValue>{formatDate(integration.createdAt)}</DetailValue>
              </DetailGrid>
            </TabPanel>
          </TabsContent>

          <TabsContent value="x834config">
            <TabPanel>
              {configLoading ? (
                <p style={{ color: 'var(--color-gray-500)', fontSize: '0.875rem' }}>Loading…</p>
              ) : (
                <X834ConfigForm
                  config={config ?? null}
                  canEdit={canMutate}
                  onSave={async (data) => {
                    if (config) {
                      await updateConfig.mutateAsync(data)
                    } else {
                      await createConfig.mutateAsync(data)
                    }
                  }}
                />
              )}
            </TabPanel>
          </TabsContent>
        </TabsContainer>
      </Tabs>
    </>
  )
}
